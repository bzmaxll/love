# build-new.gradle文件内容
apply plugin: 'java'
apply plugin: 'war'
apply plugin: 'maven-publish' 

sourceCompatibility = 1.6
version = '1.0.0-'.concat(new Date().format("yyyyMMdd"))
buildDir = 'target'
group = "com.hisupplier" 

sourceSets.main.output.classesDir = file("$buildDir/classes")
sourceSets.main.output.resourcesDir = file("$buildDir/classes")

// 版本参数
ext {
	libPath = 'target/dependency';

	aboutVersion = '1.0.0-20140922'
	mayaVersion = '1.1.0-20140416'	// 需要soap包
	hiCommonsVersion = '3.0-20160426'
	hiAccountVersion = '1.0.11-20160217'
	hiSearchVersion = '1.0.0-20160310'
	hiApiVersion = '1.0.0-20151013'
	hiCasVersion = '3.2-20120329'
	hiTaskVersion = '1.0.0-20150917'
	hiSmsVersion = '1.0.0-20140212'
	
	strutsVersion = '2.3.15.1'   	// '2.3.20.1' ongl不支持静态方法
	springVersion = '3.0.5.RELEASE'
	spring4Version = '4.0.1.RELEASE'
	ibatisVersion = '2.3.4.726'
	velocityVersion = '1.7'
	urlrewriteVersion = '4.0.4'
	wro4jVersion = '1.7.8'
	dom4jVersion = '1.6.1'
	memcachedVersion = '2.5.1'
	hessianVersion = '3.0.20'
	luceneVersion = '3.5.0'
	patchcaVersion = '0.5.0'
	backportVersion = '3.0'
	jfreeVersion = '1.0.9'
	qrcodeVersion = '3.0'
	jxlVersion = '2.6.12'
	poiVersion = '3.6-20091214'		// jxl增强
	jsonVersion = '2.2.2'			// json对象、java对象互转
	soapVersion = '2.3.1'
	httpVersion = '4.3.2'			// 模拟http提交
	pinyinVersion = '2.5.0'			// 汉字的拼音
	ehcacheVersion = '2.7.8'
	antVersion = '1.6.5'			// 文件zip压缩时 用到
	sitemeshVersion = '2.4.2'		// 不用struts插件，单独存在
	freemarkerVersion = '2.3.19'	// 不用struts插件，单独存在
	cglibVersion = '3.1'			// 如果设置proxy-target-class="true"，spring需要
	aspectjweaverVersion = '1.8.6'	// spring需要
	jstlVersion = '1.2'
	
	quartzVersion = '1.5.2'
	dnsjavaVersion = '2.0.6'
	commonsnetVersion = '3.3'
	
	jstlVersion = '1.2'
	tilesVersion = '3.0.3'
	
	jacksonVersion = '1.9.13'
}

repositories {
	maven { url 'http://192.168.1.250:8081/nexus/content/groups/public' }
	maven { url 'http://maven.springframework.org/release' }
//	maven { url 'http://maven.oschina.net/content/groups/public/' }
    mavenCentral()
}

dependencies {
	providedCompile "jsdk:jsdk:15"
	providedCompile "javamail:javamail:14"
	providedCompile "com.sun:tools:1.5.0"
	providedCompile "mysql:mysql-connector-java:5.1.35"
	
	
	compile "com.hisupplier:hisupplier-commons-en:$hiCommonsVersion"
	compile "org.slf4j:slf4j-log4j12:1.7.12"
	compile "commons-lang:commons-lang:2.6"
	compile "commons-validator:commons-validator:1.4.1"
	compile "commons-httpclient:commons-httpclient:3.1"
	compile "commons-dbcp:commons-dbcp:1.4"
	
	testCompile "junit:junit:4.+"
	testCompile "org.apache.struts:struts2-junit-plugin:$strutsVersion"
}

// 删除lib下的所有jar包
task cleanDependencies << {
	FileTree tree = fileTree(dir: libPath)  
	tree.include '*.jar' 

	tree.each { it.delete() }  
}

task copyDependencies(type: Copy, dependsOn: cleanDependencies) {
   from configurations.compile - configurations.providedCompile
   into libPath
}

// 源文件打包
task sourceJar(type: Jar) { 
    from sourceSets.main.allJava 
}

// 指定java文件的编码
[compileJava, compileTestJava]*.options*.encoding = 'GBK'

// java下的资源文件
processResources {
	from "src/main/java"
	exclude '**/*.java'
}

// 网上的配置文件
task real(type: Copy) {
    from "src/main/real"
    into sourceSets.main.output.resourcesDir
}
			
war{
	dependsOn real
	
	exclude 'WEB-INF/tmp/','WEB-INF/work/'
	
	baseName = "hisupplier"
	extension = "zip"
}

