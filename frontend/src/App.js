import "./App.css";
import MainPage from "./pages/mainPage/mainPage";
import SelectLocationPage from "./pages/selectLocationPage/selectLocation";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";

function App() {
  return (
    <div className="App">
      <Router>
        <Route exact path="/" component={MainPage} />
        <Route path="/start" component={SelectLocationPage} />
      </Router>
    </div>
  );
}

export default App;
