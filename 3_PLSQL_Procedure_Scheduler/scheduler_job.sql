BEGIN
  DBMS_SCHEDULER.create_job (
    job_name        => 'JOB_SUMMARIZE_HOURS',
    job_type        => 'STORED_PROCEDURE',
    job_action      => 'summarize_hours',
    repeat_interval => 'FREQ=WEEKLY; BYDAY=MON; BYHOUR=2; BYMINUTE=0',
    enabled         => TRUE,
    comments        => 'Weekly employee-hours summary'
  );
END;
/
