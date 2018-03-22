package io.logansquarex.processor;

import io.logansquarex.core.Constants;
import io.logansquarex.core.annotation.XBuildConfig;
import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonObject;

import java.util.Deque;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.Queue;
import java.util.Set;
@XBuildConfig(targetPkg = Constants.LOADER_PACKAGE_NAME,targetClass = Constants.LOADER_CLASS_NAME,autoMerge = true)
@JsonObject
public class SimpleCollectionModel {

    @JsonField(name = "primitive_array")
    public int[] primitiveArray;

    @JsonField(name = "model_list")
    public List<ModelForCollection> modelForCollectionList;

    @JsonField(name = "model_array_list")
    public ArrayList<ModelForCollection> modelForCollectionArrayList;

    @JsonField(name = "model_linked_list")
    public LinkedList<ModelForCollection> modelForCollectionLinkedList;

    @JsonField(name = "model_set")
    public Set<ModelForCollection> modelForCollectionSet;

    @JsonField(name = "model_queue")
    public Queue<ModelForCollection> modelForCollectionQueue;

    @JsonField(name = "model_deque")
    public Deque<ModelForCollection> modelForCollectionDeque;

    @JsonField(name = "model_map")
    public Map<String, ModelForCollection> modelForCollectionMap;

    @JsonField(name = "model_hash_map")
    public HashMap<String, ModelForCollection> modelForCollectionHashMap;

    @JsonField(name = "model_tree_map")
    public TreeMap<String, ModelForCollection> modelForCollectionTreeMap;

    @JsonField(name = "model_linked_hash_map")
    public LinkedHashMap<String, ModelForCollection> modelForCollectionLinkedHashMap;

    @JsonField(name = "model_array")
    public ModelForCollection[] modelForCollectionArray;

    @JsonObject
    public static class ModelForCollection {

        @JsonField
        public String name;

    }
}