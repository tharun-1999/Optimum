CREATE OR REPLACE PROCEDURE summarize_hours IS
BEGIN
  DELETE FROM EMP_HOURS_SUMMARY WHERE summary_week = TRUNC(SYSDATE, 'IW');
  INSERT INTO EMP_HOURS_SUMMARY(emp_id, total_hours, summary_week)
    SELECT emp_id, SUM(hours), TRUNC(SYSDATE, 'IW')
    FROM WORK_LOG
    WHERE work_date BETWEEN TRUNC(SYSDATE, 'IW') AND TRUNC(SYSDATE, 'IW')+6
    GROUP BY emp_id;
  COMMIT;
END summarize_hours;
/
