import "./App.css";
import MainPage from "./pages/mainPage/mainPage";
import SelectLocationPage from "./pages/selectLocationPage/selectLocation";
import { BrowserRouter as Router, Route } from "react-router-dom";
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
          <SelectLocationPage />
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
