plugins {
	id("net.neoforged.moddev") version "+"
//	id("me.modmuss50.mod-publish-plugin") version "+"
}
base.archivesName.set(p("mod_id"))
group = p("mod_group_id")
version = "${p("minecraft_version")}-${p("mod_version")}+${p("upper_loader")}"
java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))
tasks.jar { from("LICENSE") }
tasks.processResources {
	outputs.upToDateWhen { false }
	filesMatching("META-INF/neoforge.mods.toml") { expand(properties) }
}
neoForge {
	version = p("loader_version")
	parchment {
		mappingsVersion.set(p("parchment_version"))
		minecraftVersion.set(p("minecraft_version"))
	}
	runs {
		create("client").client()
		configureEach {
			jvmArguments.add("-XX:+AllowEnhancedClassRedefinition")
			systemProperty("terminal.jline", "true")
		}
	}
	mods.create(p("mod_id")).sourceSet(sourceSets.main.get())
}
//publishMods {
//	file.set(tasks.jar.get().archiveFile)
//	changelog.set(file("CHANGELOG.md").readText())
//	type.set(STABLE)
//	version.set(project.version.toString())
//	displayName.set("[${p("upper_loader")}] ${p("mod_name")} ${p("mod_version")}+${p("minecraft_version")}")
//	modLoaders.addAll(p("upper_loader"))
//	modrinth {
//		accessToken.set(providers.environmentVariable("MODRINTH_TOKEN"))
//		projectId.set("TlQAWQCY")
//		minecraftVersions.add(p("minecraft_version"))
//		requires("create", "cloth-config")
//	}
//	curseforge {
//		accessToken.set(providers.environmentVariable("CURSEFORGE_TOKEN"))
//		projectId.set("1233804")
//		minecraftVersions.add(p("minecraft_version"))
//		requires("create", "cloth-config")
//	}
//}
fun p(key: String) = property(key).toString()
