import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import RoutinesPage from "./pages/RoutinesPage";
import ExercisePage from "./pages/ExercisePage";
import Sidebar from "./components/layout/Sidebar";

function App() {
  return (
      <div className="App">
          <BrowserRouter>
              <div className="d-flex">
                  <Sidebar />
                  <div className="flex-grow-1 p-4">
                      <Routes>
                          <Route exact path='/' element={<RoutinesPage />} />
                          <Route path='/routines' element={<RoutinesPage />} />
                          <Route path='/exercises' element={<ExercisePage />} />
                      </Routes>
                  </div>
              </div>
          </BrowserRouter>
      </div>
  );
}

export default App;
