echo off
echo 'Generate backup file name'

set BACKUP_FILE=%date%.custom.backup

echo 'Backup path: %BACKUP_FILE%'
echo 'Creating a backup ...'

set PGPASSWORD=mojeheslo
"C:\Program Files\PostgreSQL\14\bin\pg_dump.exe" --username="student" -d BPC-BDS-PROJECT --format=custom -f "%BACKUP_FILE%"

echo 'Backup successfully created: %BACKUP_FILE%'