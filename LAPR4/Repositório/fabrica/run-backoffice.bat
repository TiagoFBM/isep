REM set the class path,
REM assumes the build was executed with maven copy-dependencies
SET BASE_CP=base.app.backoffice.console\target\base.app.backoffice.console-1.3.0-SNAPSHOT.jar;base.app.backoffice.console\target\dependency\*;

REM call the java VM, e.g, 
java -cp %BASE_CP% fabrica.app.backoffice.console.BaseBackoffice
