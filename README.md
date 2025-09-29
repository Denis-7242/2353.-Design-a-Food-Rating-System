# 🍽️ LeetCode 2353: Design a Food Rating System

## 📌 Problem Statement
You are asked to design a food rating system that can :
1. **Modify** the rating of a food item listed in the system.
2. **Return** the highest-rated food item for a type of cuisine.

Implement the `FoodRatings` class:

- `FoodRatings(String[] foods, String[] cuisines, int[] ratings)`  
  Initializes the system.  
  - `foods[i]` → name of the i-th food  
  - `cuisines[i]` → cuisine of the i-th food  
  - `ratings[i]` → initial rating of the i-th food  

- `void changeRating(String food, int newRating)`  
  Changes the rating of a given food item.

- `String highestRated(String cuisine)`  
  Returns the name of the highest-rated food for a cuisine.  
  - If there’s a tie, the **lexicographically smaller** name is chosen.

---

## 📝 Example
```java
Input:
["FoodRatings","highestRated","highestRated","changeRating","highestRated","changeRating","highestRated"]
[[["kimchi","miso","sushi","moussaka","ramen","bulgogi"],
  ["korean","japanese","japanese","greek","japanese","korean"],
  [9,12,8,15,14,7]],
 ["korean"],
 ["japanese"],
 ["sushi",16],
 ["japanese"],
 ["ramen",16],
 ["japanese"]]

Output:
[null,"kimchi","ramen",null,"sushi",null,"ramen"]

🚀 Solution (Java)
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

📊 Complexity Analysis

Time:

changeRating: O(log n)

highestRated: Amortized O(log n)

Space: O(n + number_of_updates)

🔗 References

Problem Link

Solution by Denis-7242