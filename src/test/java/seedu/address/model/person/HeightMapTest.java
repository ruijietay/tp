package seedu.address.model.person;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import seedu.address.model.person.height.Height;
import seedu.address.model.person.height.HeightEntry;
import seedu.address.model.person.height.HeightMap;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagSet;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeightMapTest {
    private static LocalDateTime timeStub = HeightEntry.getTimeOfExecution();
    private static Height heightStub = new Height(182f);

    @Test
    public void isMatch() {
        TreeMap<LocalDateTime, Height> treeMap = new TreeMap<>();
        treeMap.put(timeStub, heightStub);

        HeightMap heightMap = new HeightMap(treeMap);

        // Exact range -> returns true
        assertTrue(heightMap.isMatch(new Pair<>(182f, 182f)));

        // Falls within range -> returns true
        assertTrue(heightMap.isMatch(new Pair<>(180f, 185f)));

        // Falls outside of range -> returns false
        assertFalse(heightMap.isMatch(new Pair<>(160f, 169f)));

        // Incorrect generic types -> returns false
        assertFalse(heightMap.isMatch(new Pair<Object, Object>("foo", "bar")));
        assertFalse(heightMap.isMatch(new Pair<Float, Object>(182f, "bar")));
        assertFalse(heightMap.isMatch(new Pair<Object, Float>("foo", 182f)));

        // Null pair -> returns false
        assertFalse(heightMap.isMatch(new Pair<Float, Float>(null, null)));

        // Either value is null -> returns false
        assertFalse(heightMap.isMatch(new Pair<Float, Float>(180f, null)));
        assertFalse(heightMap.isMatch(new Pair<Float, Float>(null, 180f)));
    }

    @Test
    public void equals() {
        Tag friendTag = new Tag("friend");
        Tag loverTag = new Tag("lover");
        Tag invalidTag = new Tag("invalid");

        Set<Tag> testSet = new HashSet<Tag>(Arrays.asList(friendTag, loverTag));

        TagSet tagSet = new TagSet(testSet);

        // Same tagset
        assertTrue(tagSet.equals(tagSet));

        // Different tagset, same values
        assertTrue(tagSet.equals(new TagSet(testSet)));

        // Empty tagset
        assertFalse(tagSet.equals(new TagSet(new HashSet<>())));

        // Partial tagset
        assertFalse(tagSet.equals(new TagSet(new HashSet<Tag>(Arrays.asList(loverTag)))));

        // Additional tag in tagset
        assertFalse(tagSet.equals(new TagSet(new HashSet<>(Arrays.asList(friendTag, loverTag, invalidTag)))));
    }
}