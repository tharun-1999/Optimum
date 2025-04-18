@Bean
public JdbcTemplate jdbcTemplate(DataSource ds) {
  JdbcTemplate jt = new JdbcTemplate(ds);
  jt.setFetchSize(1_000);
  return jt;
}

@Service
public class LargeReadService {
  private final JdbcTemplate jdbc;

  public LargeReadService(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public void processAll() {
    jdbc.query(
      con -> {
        PreparedStatement ps = con.prepareStatement(
          "SELECT id, data FROM MY_TABLE",
          ResultSet.TYPE_FORWARD_ONLY,
          ResultSet.CONCUR_READ_ONLY
        );
        ps.setFetchSize(1_000);
        return ps;
      },
      (ResultSet rs) -> {
        while (rs.next()) {
          long id = rs.getLong("id");
          String data = rs.getString("data");
          // process each record
        }
      }
    );
  }
}

@RestController
@RequestMapping("/api/records")
public class RecordsController {
  private final LargeReadService svc;

  public RecordsController(LargeReadService svc) {
    this.svc = svc;
  }

  @GetMapping("/process")
  public ResponseEntity<String> run() {
    svc.processAll();
    return ResponseEntity.ok("Processed 1M records");
  }
}
