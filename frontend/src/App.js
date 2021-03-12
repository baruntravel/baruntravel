import "./App.css";
import MainPage from "./pages/mainPage/mainPage";
<<<<<<< HEAD
import SelectAreaPage from "./pages/selectAreaPage/selectAreaPage";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
=======
import SelectLocationPage from "./pages/selectLocationPage/selectLocation";
import { BrowserRouter as Router, Route } from "react-router-dom";
>>>>>>> 0cacb1952c161d3f44b65bc8a97eb110ad4a94ee
import UsersRoutePage from "./pages/usersRoutePage/usersRoutePage";
import HotplacePage from "./pages/hotPlacePage/hotplacePage";
import { RecoilRoot } from "recoil";

function App() {
  return (
    <RecoilRoot>
      <Router>
        <Route exact path="/">
          <MainPage />
        </Route>
        <Route path="/start">
          <SelectAreaPage />
        </Route>
        <Route exact path="/:id/places">
          <HotplacePage />
        </Route>
        <Route exact path="/:id/routes">
          <UsersRoutePage />
        </Route>
      </Router>
    </RecoilRoot>
  );
}

export default App;
