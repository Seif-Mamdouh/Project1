public class ColorBlending {
    // A Color class to hold RGB values
    // Each value is a byte, since the range of RGB values is 0-255
    static class Color {
        byte r;
        byte g;
        byte b;

        // Parses a comma-separated RGB string (e.g., "1,2,3") into a Color object
        static Color parse(String input) {
            String[] parts = input.split(",");
            
            // Bug: By using Integer.parseInt() with a default of 0, 
            // anything that can't be parsed will be replaced with 0
            // r,g,b will be parsed as 0,0,0 instead of throwing an error
            byte[] rgbValues = new byte[3];
            for (int i = 0; i < parts.length; i++) {
                try {
                    rgbValues[i] = (byte) Integer.parseInt(parts[i].trim());
                } catch (NumberFormatException e) {
                    rgbValues[i] = 0;
                }
            }

            if (parts.length != 3) {
                throw new IllegalArgumentException("RGB values must have exactly 3 components");
            }

            Color color = new Color();
            color.r = rgbValues[0];
            color.g = rgbValues[1];
            color.b = rgbValues[2];
            return color;
        }
    }

    // Blends two colors by averaging the RGB values
    static Color blendColors(Color color1, Color color2) {
        // Bug: Incorrect order of operations
        // This won't add the colors together and then divide by two
        // Instead, it will divide color2's value by two and then add it to color1
        byte r = (byte) (color1.r + color2.r / 2);
        byte g = (byte) (color1.g + color2.g / 2);
        byte b = (byte) (color1.b + color2.b / 2);

        // Bug: Since these are bytes, there's a high chance of overflow
        // For example, if color1.b is 200 and color2.b is 240, 
        // the result of adding them together will be too large to fit in a byte

        Color blendedColor = new Color();
        blendedColor.r = r;
        blendedColor.g = g;
        blendedColor.b = b;
        return blendedColor;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println(
                "Usage: java ColorBlending <color1> <color2>\n" +
                "Example: java ColorBlending 1,2,3 25,67,255"
            );
            System.exit(1);
        }

        Color color1 = Color.parse(args[0]);
        Color color2 = Color.parse(args[1]);

        Color blendedColor = blendColors(color1, color2);

        System.out.printf(
            "Blended Color: r=%d, g=%d, b=%d%n", 
            blendedColor.r, blendedColor.g, blendedColor.b
        );
    }
}