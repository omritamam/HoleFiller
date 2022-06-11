package HoleFillerRunner;

public class CmdUtils {
    public static void printUsage() {
        System.out.println("Usage: java -jar HoleFiller.jar <input_image> <output_image> <connectivity_type> <weight_function>");
        System.out.println("Connectivity types: 4 or 8");
    }
//    public HoleFillerSettings parseCommandLineArgs(String[] args) {
//        if (args.length != 4) {
//            printUsage();
//            System.exit(1);
//        }
//        String inputImageFile = args[0];
//        String outputImageFile = args[1];
//        ConnectivityType connectivity = ConnectivityType.fromString(args[2]);
//        WeightFunction weightFunction = WeightFunction.fromString(args[3]);
//        return new HoleFillerSettings(inputImageFile, outputImageFile, connectivity, weightFunction);
//
//    }
}
