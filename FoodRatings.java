import java.util.*;

class FoodRatings {
    private static class Entry {
        String food;
        int rating;
        Entry(String f, int r) { food = f; rating = r; }
    }

    private final Map<String, String> foodToCuisine = new HashMap<>();
    private final Map<String, Integer> foodToRating = new HashMap<>();
    private final Map<String, PriorityQueue<Entry>> cuisineToPQ = new HashMap<>();

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        for (int i = 0; i < foods.length; i++) {
            String food = foods[i];
            String cuisine = cuisines[i];
            int rating = ratings[i];

            foodToCuisine.put(food, cuisine);
            foodToRating.put(food, rating);

            cuisineToPQ.computeIfAbsent(cuisine, k -> new PriorityQueue<>(
                (a, b) -> {
                    if (a.rating != b.rating) return b.rating - a.rating;
                    return a.food.compareTo(b.food);
                }
            )).add(new Entry(food, rating));
        }
    }

    public void changeRating(String food, int newRating) {
        String cuisine = foodToCuisine.get(food);
        foodToRating.put(food, newRating);
        cuisineToPQ.get(cuisine).add(new Entry(food, newRating));
    }

    public String highestRated(String cuisine) {
        PriorityQueue<Entry> pq = cuisineToPQ.get(cuisine);
        while (true) {
            Entry top = pq.peek();
            if (top == null) return "";
            int current = foodToRating.get(top.food);
            if (top.rating == current) return top.food;
            pq.poll(); // stale entry
        }
    }
}
