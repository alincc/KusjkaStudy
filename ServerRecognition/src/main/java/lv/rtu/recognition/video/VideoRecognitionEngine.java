package lv.rtu.recognition.video;

import lv.rtu.db.UserTableImplementationDAO;
import lv.rtu.domain.User;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.face.alignment.AffineAligner;
import org.openimaj.image.processing.face.feature.comparison.LtpDtFeatureComparator;
import org.openimaj.image.processing.face.feature.ltp.LtpDtFeature;
import org.openimaj.image.processing.face.feature.ltp.TruncatedWeighting;
import org.openimaj.image.processing.face.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.keypoints.KEDetectedFace;
import org.openimaj.image.processing.face.recognition.SimpleKNNRecogniser;

import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class VideoRecognitionEngine {

    private static UserTableImplementationDAO table = new UserTableImplementationDAO();
    private static FKEFaceDetector engine = new FKEFaceDetector();
    private static SimpleKNNRecogniser<LtpDtFeature, KEDetectedFace> recogniser = new SimpleKNNRecogniser<LtpDtFeature, KEDetectedFace>(
            new LtpDtFeature.Factory<KEDetectedFace>(new AffineAligner(), new TruncatedWeighting()),
            new LtpDtFeatureComparator(),
            4);


    public static String recognise(BufferedImage bImageFromConvert) {
        String result = null;
        List<KEDetectedFace> faces = engine.detectFaces(ImageUtilities.createFImage(bImageFromConvert));
        if (faces.size() == 1) {
            result = recogniser.queryBestMatch(faces.get(0)).toString();
            System.out.println("Looks like: " + recogniser.queryBestMatch(faces.get(0)));
        } else {
            System.out.println("Wrong number of faces found");
        }
        return result;
    }

    public static void trainRecognizer(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        String fileName;
        String fullPath;
        BufferedImage originalImage = null;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fullPath = listOfFiles[i].getName();
                fileName = fullPath.split("-")[0];
                System.out.println("File " + fullPath);
                try {
                    originalImage = ImageIO.read(new File(".\\resources\\data\\images\\training\\" + fullPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                originalImage = convertToGray(originalImage);
                FImage image = ImageUtilities.createFImage(originalImage);
                List<KEDetectedFace> faces = engine.detectFaces(image);
                recogniser.addInstance((((User) table.findUserByImageFileName(fullPath)).getId()).toString(), faces.get(0));
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        recogniser.train();
    }

    private static BufferedImage convertToGray(BufferedImage image) {
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(image, image);
        return image;
    }

}
