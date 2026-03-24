//package com.example.demo.helper;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.vision.v1.*;
//import com.google.protobuf.ByteString;
//import org.apache.commons.io.output.ByteArrayOutputStream;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.FileInputStream;
//import java.util.List;
//
//public class GoogleVisionService {
//
//    public static String extractText(List<BufferedImage> images) {
//
//        StringBuilder fullText = new StringBuilder();
//
//        try {
//
//            // ✅ LOAD JSON KEY DIRECTLY
//            GoogleCredentials credentials = GoogleCredentials
//                    .fromStream(new FileInputStream("C:/keys/pdf-ocr-project-491110-faa42936e5fc.json"));
//
//            ImageAnnotatorSettings settings =
//                    ImageAnnotatorSettings.newBuilder()
//                            .setCredentialsProvider(() -> credentials)
//                            .build();
//
//            ImageAnnotatorClient client =
//                    ImageAnnotatorClient.create(settings);
//
//            // 🔁 LOOP IMAGES
//            for (BufferedImage image : images) {
//
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                ImageIO.write(image, "png", baos);
//
//                ByteString imgBytes = ByteString.copyFrom(baos.toByteArray());
//
//                Image img = Image.newBuilder().setContent(imgBytes).build();
//
//                Feature feat = Feature.newBuilder()
//                        .setType(Feature.Type.TEXT_DETECTION)
//                        .build();
//
//                AnnotateImageRequest request =
//                        AnnotateImageRequest.newBuilder()
//                                .addFeatures(feat)
//                                .setImage(img)
//                                .build();
//
//                BatchAnnotateImagesResponse response =
//                        client.batchAnnotateImages(List.of(request));
//
//                for (AnnotateImageResponse res : response.getResponsesList()) {
//
//                    if (res.hasError()) {
//                        System.out.println(res.getError().getMessage());
//                        continue;
//                    }
//
//                    String text = res.getFullTextAnnotation().getText();
//                    fullText.append(text).append("\n");
//                }
//            }
//
//            client.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return fullText.toString();
//    }
//}