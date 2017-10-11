package by.bsuir.kanban.service.util;

import by.bsuir.kanban.domain.Tag;
import by.bsuir.kanban.domain.Task;

import java.util.Comparator;
import java.util.Set;

/**
 * Created by vladislav on 22.05.17.
 */
public class RelevantTaskComparator implements Comparator<Task> {

    private static final int GREATER = 1;
    private static final int EQUALS = 0;
    private static final int LESS = -1;
    private Set<Tag> searchedTasks;

    public RelevantTaskComparator(Set<Tag> searchedTasks) {
        this.searchedTasks = searchedTasks;
    }

    @Override
    public int compare(Task task1, Task task2) {
        int task1ContainsCount = 0;
        int task2ContainsCount = 0;

        for(Tag tag: searchedTasks){

            if(task1.getTags().contains(tag)){
                task1ContainsCount++;
            }

            if(task2.getTags().contains(tag)){
                task2ContainsCount++;
            }

        }

        if(task1ContainsCount < task2ContainsCount){
            return LESS;
        }
        else if(task1ContainsCount > task2ContainsCount){
            return GREATER;
        }
        else {
            return EQUALS;
        }

    }
}
