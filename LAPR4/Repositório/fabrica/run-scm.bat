REM set the class path,
REM assumes the build was executed with maven copy-dependencies
SET BASE_CP=base.app.scm\target\base.app.scm-1.3.0-SNAPSHOT.jar;base.app.scm\target\dependency\*;

REM call the java VM, e.g, 
java -cp %BASE_CP% fabrica.scm.messagesmanagement.BaseSCM
