package com.chengjs.cjsssmsweb.common.miniui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtil {
    
    public static ArrayList ToTree(List table, String childrenField, String idField, String parentIdField)
    {
    	ArrayList tree = new ArrayList();

        Map hash = new HashMap();
        for (int i = 0, l = table.size(); i < l; i++)
        {
            Map t = (Map)table.get(i);
            hash.put(t.get(idField), t);
        }
  
        for (int i = 0, l = table.size(); i < l; i++)
        {
            Map t = (Map)table.get(i);
            Object parentID = t.get(parentIdField);
            if (parentID == null || parentID.toString().equals("-1"))   
            {
                tree.add(t);
                continue;
            }
            Map parent = (Map)hash.get(parentID);
            if (parent == null)    
            {
                tree.add(t);
                continue;
            }
            List children = (List)parent.get(childrenField);
            if (children == null)
            {
                children = new ArrayList();
                parent.put(childrenField, children);
            }
            children.add(t);
        }
        
        SyncTreeNodes(tree, 1, "", childrenField);
        
        return tree;
    }
    private static void SyncTreeNodes(ArrayList nodes, int outlineLevel, String outlineNumber, String childrenField)
    {
        for (int i = 0, l = nodes.size(); i < l; i++)
        {
            HashMap node = (HashMap)nodes.get(i);

            node.put("OutlineLevel", outlineLevel);
            node.put("OutlineNumber", outlineNumber + (i + 1));            

            ArrayList childNodes = (ArrayList)node.get(childrenField);

            if (childNodes != null && childNodes.size() > 0)
            {
                SyncTreeNodes(childNodes, outlineLevel + 1, node.get("OutlineNumber").toString() + ".",  childrenField);
            }
        }
    }    

    public static ArrayList ToList(List tree, String parentId, String childrenField, String idField, String parentIdField)
    {
    	ArrayList list = new ArrayList();
        for (int i = 0, len = tree.size(); i < len; i++)
        {
            Map task = (Map)tree.get(i);

            task.put(parentIdField, parentId);
            list.add(task);
        	
            List children = (List)task.get(childrenField);

            if (children != null && children.size() > 0)
            {
            	String id = task.get(idField) == null ?"" : task.get(idField).toString();
                List list2 = ToList(children, id, childrenField, idField, parentIdField);
                list.addAll(list2);
            }
            //task.remove(childrenField);
        }
        return list;
    }
}
