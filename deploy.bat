
"D:/work/tools/maven/default\bin\mvn" deploy:deploy-file -DgroupId=cn.net.vidyo -DartifactId=dylink-data   -Dversion=2.0.18.23.RELEASE -Dpackaging=jar -Dfile=D:\work\projects\dylink\dylink\dylink-common\dylink-data\target\dylink-data-2.0.18.23.RELEASE.jar     -Durl=http://maven.vdyoo.cn/repository/maven-releases/ -DrepositoryId=vdyoo

pause

"D:/work/tools/maven/default\bin\mvn" deploy:deploy-file -DgroupId=cn.net.vidyo -DartifactId=dylink-common -Dversion=2.0.18.23.RELEASE -Dpackaging=jar -Dfile=D:\work\projects\dylink\dylink\dylink-common\dylink-common\target\dylink-common-2.0.18.23.RELEASE.jar -Durl=http://maven.vdyoo.cn/repository/maven-releases/ -DrepositoryId=vdyoo
