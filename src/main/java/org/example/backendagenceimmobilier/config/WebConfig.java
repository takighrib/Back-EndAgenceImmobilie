package org.example.backendagenceimmobilier.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.path:uploads/images}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Obtenir le chemin absolu
        String absolutePath = new File(uploadPath).getAbsolutePath();

        // Configurer Spring Boot pour servir les images
        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations("file:" + absolutePath + "/");

        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║  🖼️  Configuration Upload d'Images                    ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println("✅ Images servies depuis: " + absolutePath);
        System.out.println("🌐 URL d'accès: http://localhost:8080/uploads/images/");
        System.out.println("════════════════════════════════════════════════════════");
    }
}