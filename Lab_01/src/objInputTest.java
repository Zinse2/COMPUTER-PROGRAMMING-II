public class objInputTest
{
    public static void main(String[] args)
    {
        SafeInputObj in = new SafeInputObj();

        String word = in.getNonZeroLenString("Please enter a word");
        System.out.println("You entered: " + word);

        int rangedInt = in.getRangedInt("Please enter a number between 1 and 10", 1, 10);
        System.out.println("Valid ranged int: " + rangedInt);

        int anyInt = in.getInt("Please enter any integer");
        System.out.println("You entered int: " + anyInt);

        double rangedDouble = in.getRangedDouble("Please enter a double between 1 and 10", 1, 10);
        System.out.println("Valid ranged double: " + rangedDouble);

        double anyDouble = in.getDouble("Please enter any double");
        System.out.println("You entered double: " + anyDouble);

        boolean confirm = in.getYNConfirm("Continue? (Y/N)");
        System.out.println("Confirmation: " + confirm);
    }
}
