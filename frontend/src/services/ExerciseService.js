import axios from "axios";

const BASE_URL = "http://localhost:8080/api/exercises";

class ExerciseService {

    getAllExercises() {
        return axios.get(BASE_URL);
    }

    createExercise(exercise){
        return axios.post(BASE_URL, exercise);
    }

    getExerciseById(exerciseId){
        return axios.get(`${BASE_URL}/${exerciseId}`);
    }

    updateExercise(exerciseId, exercise){
        return axios.put(`${BASE_URL}/${exerciseId}`, exercise);
    }

    deleteExercise(exerciseId){
        return axios.delete(`${BASE_URL}/${exerciseId}`);
    }

    getMuscles(){
        return axios.get(`${BASE_URL}/muscles`);
    }

}

export default new ExerciseService();
