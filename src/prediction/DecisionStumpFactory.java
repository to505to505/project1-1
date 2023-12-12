package prediction;

import java.util.ArrayList;

public class DecisionStumpFactory {
    
    public static DS createDecisionStumpBasic(double[][] data, String course_name, String property_name, String[] courses_names ) {
        int property_col_num = 0;
        int course_col_num = 0;

        /* Transforming course_name and property_name to the indecies in courses_names 
        array (basically getting column numbers of the course and property) */

        for (int k = 0; k < courses_names.length; k++) {
            if (courses_names[k].equals(property_name)) {
                property_col_num = k;
            }
            if (courses_names[k].equals(course_name)) {
                course_col_num = k;
            }
        }

        /* Getting feature type of property and creating different classes based on this knowledge */
        boolean is_categorical = false;
            switch (courses_names[property_col_num]) {
                case "Suruna Value":
                    is_categorical = true;
                    break;
                case "Hurni Level":
                    is_categorical = true;
                    break;
                case "Volta ":
                    is_categorical = true;
                    break;
        }


        /* Creating class */
        if(is_categorical) {
            return new DSCat(data, course_name, property_name, courses_names, course_col_num, property_col_num);
        } else {
            return new DSNum(data, course_name, property_name, courses_names, course_col_num, property_col_num);
        }
   
}

    public static Prediction createSoloPrediction(double[][] data,int student_id, int[] students_ids, String course_name,  String[] courses_names ) {
        // Tranforming course_name int cource column number
        int course_col_num = 0;
        for (int k = 0; k < courses_names.length; k++) {
            if (courses_names[k].equals(course_name)) {
                course_col_num = k;
            }
        }


        ArrayList<String> forbidden_courses = new ArrayList<>();
        int student_index = get_student_index(student_id, students_ids);
        DS bestDecisionStump = find_best_split(data, student_id, student_index, students_ids, course_name, courses_names, forbidden_courses);
        ArrayList<DS> used_splits = new ArrayList<>();
        used_splits.add(bestDecisionStump);
        Prediction prediction_for_1 = new Prediction(data, used_splits, student_index, student_id);
        return prediction_for_1;

        }
    

    public static Prediction createMultiplePrediction(double[][] data,int student_id, int[] students_ids, String course_name,  String[] courses_names, int properties_amount) {
        // Tranforming course_name int cource column number
        int course_col_num = 0;
        for (int k = 0; k < courses_names.length; k++) {
            if (courses_names[k].equals(course_name)) {
                course_col_num = k;
            }
        }

        ArrayList<String> forbidden_courses = new ArrayList<>();
        ArrayList<DS> used_splits = new ArrayList<>();
        int student_index = get_student_index(student_id, students_ids);
        for(int i =0; i<properties_amount; i++) {
            DS bestDecisionStump = find_best_split(data, student_id, student_index, students_ids, course_name, courses_names, forbidden_courses);
            String forbidden_course = bestDecisionStump.get_property_name();
            forbidden_courses.add(forbidden_course);
            used_splits.add(bestDecisionStump);
        }
       
        Prediction multiple_prediction = new Prediction(data, used_splits, student_index, student_id);
        return multiple_prediction;

    }
    private static DS find_best_split(double[][] data, int student_id, int student_index, int[] students_ids, String course_name, String[] courses_names, ArrayList<String> forbidden_courses) {
        
        // setting up variables to save the best decision stump
        double best_variance = 100.0;
        DS bestDecisionStump = null;

    

        /*   adding courses where a student doesnt have a grade 
        to the forbidden courses that won't be used to predict */
        for(int j = 0; j<courses_names.length; j++) {
            if(data[student_index][j] < 0) 
                forbidden_courses.add(courses_names[j]);
        }
    

        for (String item : courses_names) {
            if (course_name.equals(item) || (item.equals("TSO-010")) || (item.equals("PLO-132"))
                || (item.equals("BKO-801")) || forbidden_courses.contains(item) ) {

            continue;
            } else {
                DS decisionStump = DecisionStumpFactory.createDecisionStumpBasic(data, course_name, item, courses_names);
                double variance_of_iteration = decisionStump.get_total_variance(); 
                if(variance_of_iteration<best_variance) {
                    best_variance = variance_of_iteration;
                    bestDecisionStump = decisionStump;
                }
                }
        }
        return bestDecisionStump;
        
    }
        public static int get_student_index(int student_id, int[] students_ids) {
            /// getting student index (row number) out of studentID
            int student_index = 0;
            for (int i = 0; i < students_ids.length; i++) {
                if (students_ids[i] == student_id) {
                    student_index = (int) i;
                    break;
            }
        }
        return student_index;
    }
}

