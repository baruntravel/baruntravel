import "./App.css";
import MainPage from "./pages/mainPage/mainPage";
import SelectLocationPage from "./pages/selectLocationPage/selectLocation";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import UsersRoutePage from "./pages/usersRoutePage/usersRoutePage";
import HotplacesPage from "./pages/hotplacesPage/hotplacePages";

function App() {
  return (
    <div className="App">
      <Router>
        <Route exact path="/" component={MainPage} />
        <Route path="/start" component={SelectLocationPage} />
        <Route exact path="/:id/places" component={HotplacesPage} />
        <Route exact path="/:id/routes" component={UsersRoutePage} />
      </Router>
    </div>
  );
}

export default App;
