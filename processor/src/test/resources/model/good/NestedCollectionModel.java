package model.good;

import io.logansquarex.core.Constants;
import io.logansquarex.core.annotation.XBuildConfig;
import io.logansquarex.core.annotation.JsonField;
import io.logansquarex.core.annotation.JsonObject;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
@XBuildConfig(targetPkg = Constants.LOADER_PACKAGE_NAME,targetClass = Constants.LOADER_CLASS_NAME,autoMerge = true)
@JsonObject
public class NestedCollectionModel {

    @JsonField(name = "model_list")
    public List<List<ModelForCollection>> modelForCollectionList;

    @JsonField(name = "model_set")
    public Set<List<ModelForCollection>> modelForCollectionSet;

    @JsonField(name = "model_queue")
    public Queue<List<ModelForCollection>> modelForCollectionQueue;

    @JsonField(name = "model_deque")
    public Deque<List<ModelForCollection>> modelForCollectionDeque;

    @JsonField(name = "model_map")
    public Map<String, List<ModelForCollection>> modelForCollectionMap;

    @JsonField
    public List<String[]> arrayList;

    @JsonField
    public List<List<Map<String, ModelForCollection>>[]> complexArray;

    @JsonField
    public List<List<Map<String, ModelForCollection[]>>[]> complexerArray;

    @JsonField
    public Map<String, Map<String, ArrayList<List<Map<String, ModelForCollection>>>>> dontKnowWhatImDoingMap;

    @JsonField
    public List<List<List<ArrayList<ModelForCollection>>>> dontKnowWhatImDoingList;

    @JsonObject
    public static class ModelForCollection {

        @JsonField
        public String name;

    }
}