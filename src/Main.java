import java.util.*;

public class Main {

    private static final Random random = new Random();

    //提供隋機數字range Function
    public static int getRandomRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    //提供隋機數字 Function
    public static int getRandom(int x) {
        return random.nextInt(x);
    }

    //抽走2卡Function
    public static void extractPairs(ArrayList<String> list, ArrayList<String> rubbishBox) {

        for (int j = 0; j <= list.size(); j++) {
            for (int i = j + 1; i <= list.size() - 1; i++) {
                String value = list.get(j);
                String value2 = list.get((i));

                if (value.equals(value2)) {
                    list.remove(i);
                    list.remove(j);
                    rubbishBox.add(value);
                    rubbishBox.add(value2);
                    j = 0;
                    i = 1;
                }
            }
        }

    }

    //抽牌加入玩家 Function
    public static void drawCards(List<String> initial, ArrayList<String> list) {

        for (int i = 0; i < getRandomRange(11, 13); i++) {

            int drawCard = getRandom(initial.size()); // 隨機抽牌
            list.add(initial.get(drawCard)); // 加入玩家手牌
            initial.remove(drawCard); // 從牌堆移除

        }

    }

    //電腦抽牌
    public static void computerDrawCard(int cardsNO, ArrayList<String> one, ArrayList<String> two) {

        int randomCV = getRandom(cardsNO);
        one.add(two.get(randomCV));
        two.remove(randomCV);
    }

    //玩家抽牌
    public static void playerDrawCard(int cardsNO, ArrayList<String> one, ArrayList<String> two) {



        Scanner scanner = new Scanner(System.in);
        System.out.println("請輸入拿取牌位置: " + "1 to " + cardsNO);

        boolean loop = false;
        do {
            int cardLocate = scanner.nextInt();
            if (cardLocate < 1 || cardLocate > cardsNO) {
                System.out.println("請輸入1 to " + cardsNO);
                loop = true;
            } else {
                one.add(two.get(cardLocate - 1));
                two.remove((cardLocate - 1));

                System.out.println("玩家手牌數:  " + one);
                loop = false;
            }
        } while (loop);

    }


    //WIN
    public static boolean win(int p, int c1, int c2, int c3) {


        int totalCards = p + c1 + c2 + c3;

        return totalCards != 1;
    }

    public static void main(String[] args) throws InterruptedException {

        String[] standardValues = {"A", "2", "3", "4", "5", "6", "7", //開牌組下
                "8", "9", "T", "J", "Q", "K"};
        List<String> initialBox = new ArrayList<>();
        for (String value : standardValues) {
            initialBox.addAll(Collections.nCopies(4, value));
        }
        initialBox.add("G");                 //開牌組上
        ArrayList<String> rubbishBox = new ArrayList<>();           //開垃圾箱

        Player player = new Player(new ArrayList<>());
        Player computer1 = new Player(new ArrayList<>());
        Player computer2 = new Player(new ArrayList<>());
        Player computer3 = new Player(new ArrayList<>());


        drawCards(initialBox, computer1.getCardsBox());// 加入電腦1手牌
        drawCards(initialBox, computer2.getCardsBox());// 加入電腦2手牌
        drawCards(initialBox, computer3.getCardsBox());// 加入電腦3手牌
        player.getCardsBox().addAll(initialBox); // 加入玩家手牌
        initialBox.clear(); // 從牌堆移除所有牌&加入玩家牌上

        System.out.println("玩家: 你的牌如下");
        System.out.println(player.getCardsBox());
        System.out.println("5秒後自動抽出成對的牌");
        //Thread.currentThread().sleep(5000);


        extractPairs(computer1.getCardsBox(), rubbishBox); //自動抽出2張牌
        extractPairs(computer2.getCardsBox(), rubbishBox);
        extractPairs(computer3.getCardsBox(), rubbishBox);
        extractPairs(player.getCardsBox(), rubbishBox);    //自動抽出2張牌
        System.out.println("玩家: 自動抽出後的牌組");
        System.out.println(player.getCardsBox());



        do {
            //player 抽牌
            if (!player.getCardsBox().isEmpty()) {

                if (!computer1.getCardsBox().isEmpty()) {
                    System.out.println("對方手牌數(電腦1:  " + computer1.getCardsBox().size());
                    playerDrawCard(computer1.getCardsBox().size(), player.getCardsBox(), computer1.getCardsBox());
                    extractPairs(player.getCardsBox(), rubbishBox);

                } else if (!computer2.getCardsBox().isEmpty()) {
                    System.out.println("對方手牌數(電腦2:  " + computer2.getCardsBox().size());
                    playerDrawCard(computer2.getCardsBox().size(), player.getCardsBox(), computer2.getCardsBox());
                    extractPairs(player.getCardsBox(), rubbishBox);

                } else if (!computer3.getCardsBox().isEmpty()) {
                    System.out.println("對方手牌數(電腦3:  " + computer3.getCardsBox().size());
                    playerDrawCard(computer3.getCardsBox().size(), player.getCardsBox(), computer3.getCardsBox());
                    extractPairs(player.getCardsBox(), rubbishBox);
                }
            }


            //1抽2
            if (!computer1.getCardsBox().isEmpty()) {
                if (computer2.getCardsBox().size() > 0) {
                    computerDrawCard(computer2.getCardsBox().size(), computer1.getCardsBox(), computer2.getCardsBox());
                    extractPairs(computer1.getCardsBox(), rubbishBox);

                } else if (computer3.getCardsBox().size() > 0) {
                    computerDrawCard(computer3.getCardsBox().size(), computer1.getCardsBox(), computer3.getCardsBox());
                    extractPairs(computer1.getCardsBox(), rubbishBox);

                } else if (player.getCardsBox().size() > 0) {
                    computerDrawCard(player.getCardsBox().size(), computer1.getCardsBox(), player.getCardsBox());
                    extractPairs(computer1.getCardsBox(), rubbishBox);
                }
            }


            //2抽3
            if (computer2.getCardsBox().size() != 0) {
                if (computer3.getCardsBox().size() > 0) {
                    computerDrawCard(computer3.getCardsBox().size(), computer2.getCardsBox(), computer3.getCardsBox());
                    extractPairs(computer2.getCardsBox(), rubbishBox);

                } else if (player.getCardsBox().size() > 0) {
                    computerDrawCard(player.getCardsBox().size(), computer2.getCardsBox(), player.getCardsBox());
                    extractPairs(computer2.getCardsBox(), rubbishBox);

                } else if (computer1.getCardsBox().size() > 0) {
                    computerDrawCard(computer1.getCardsBox().size(), computer2.getCardsBox(), computer1.getCardsBox());
                    extractPairs(computer2.getCardsBox(), rubbishBox);

                }
            }


            //3抽player
            if (computer3.getCardsBox().size() != 0) {
                if (player.getCardsBox().size() > 0) {
                    computerDrawCard(player.getCardsBox().size(), computer3.getCardsBox(), player.getCardsBox());
                    extractPairs(computer3.getCardsBox(), rubbishBox);

                } else if (computer1.getCardsBox().size() > 0) {
                    computerDrawCard(computer1.getCardsBox().size(), computer3.getCardsBox(), computer1.getCardsBox());
                    extractPairs(computer3.getCardsBox(), rubbishBox);

                } else if (computer2.getCardsBox().size() > 0) {
                    computerDrawCard(computer2.getCardsBox().size(), computer3.getCardsBox(), computer2.getCardsBox());
                    extractPairs(computer3.getCardsBox(), rubbishBox);
                }
            }


            System.out.println("COM1" + computer1.getCardsBox());
            System.out.println("COM2" + computer2.getCardsBox());
            System.out.println("COM3" + computer3.getCardsBox());
            System.out.println("PLAY" + player.getCardsBox());


        } while (win(player.getCardsBox().size(),computer1.getCardsBox().size(),
                computer2.getCardsBox().size(),computer3.getCardsBox().size()));


    }
}
