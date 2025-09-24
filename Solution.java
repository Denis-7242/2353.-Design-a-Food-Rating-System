import java.util.*;

public class Solution {
    private static class Entry {
        String food;
        int rating;
        Entry(String f, int r) { food = f; rating = r; }
    }

    private final Map<String, String> foodToCuisine = new HashMap<>();
    private final Map<String, Integer> foodToRating = new HashMap<>();
    private final Map<String, PriorityQueue<Entry>> cuisineToPQ = new HashMap<>();

    // Constructor
    public Solution(String[] foods, String[] cuisines, int[] ratings) {
        for (int i = 0; i < foods.length; i++) {
            String food = foods[i];
            String cuisine = cuisines[i];
            int rating = ratings[i];

            foodToCuisine.put(food, cuisine);
            foodToRating.put(food, rating);

            cuisineToPQ.computeIfAbsent(cuisine, k -> new PriorityQueue<>(
                (a, b) -> {
                    if (a.rating != b.rating) return b.rating - a.rating; // higher rating first
                    return a.food.compareTo(b.food); // lexicographically smaller first
                }
            )).add(new Entry(food, rating));
        }
    }

    // Update rating
    public void changeRating(String food, int newRating) {
        String cuisine = foodToCuisine.get(food);
        foodToRating.put(food, newRating);
        cuisineToPQ.get(cuisine).add(new Entry(food, newRating)); // push new entry
    }

    // Get highest rated food for a cuisine
    public String highestRated(String cuisine) {
        PriorityQueue<Entry> pq = cuisineToPQ.get(cuisine);
        while (true) {
            Entry top = pq.peek();
            if (top == null) return "";
            int current = foodToRating.get(top.food);
            if (top.rating == current) return top.food; // valid entry
            pq.poll(); // remove stale entry
        }
    }

    // ✅ Main method
    public static void main(String[] args) {
        String[] foods = {"kimchi","miso","sushi","moussaka","ramen","bulgogi"};
        String[] cuisines = {"korean","japanese","japanese","greek","japanese","korean"};
        int[] ratings = {9,12,8,15,14,7};

        Solution fr = new Solution(foods, cuisines, ratings);

        System.out.println(fr.highestRated("korean"));   // kimchi
        System.out.println(fr.highestRated("japanese")); // ramen
        fr.changeRating("sushi", 16);
        System.out.println(fr.highestRated("japanese")); // sushi
        fr.changeRating("ramen", 16);
        System.out.println(fr.highestRated("japanese")); // ramen
    }
}
