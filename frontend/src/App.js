import "./App.css";
import MainPage from "./pages/mainPage/mainPage";
import SelectContainer from "./pages/selectContainer/selectContainer";
import { BrowserRouter as Router, Route } from "react-router-dom";
import UsersRoutePage from "./pages/usersRoutePage/usersRoutePage";
import HotplacePage from "./pages/hotplacePage/hotplacePage";
import { RecoilRoot } from "recoil";
import MyRoutePage from "./pages/myRoutePage/myRoutePage";
import PlaceCard from "./components/placeCard/placeCard";
import RouteDetailPage from "./pages/usersRoutePage/routeDetailPage/routeDetailPage";

function App() {
  return (
    <RecoilRoot>
      <Router>
        <Route exact path="/">
          <MainPage />
        </Route>
        <Route path="/start">
          <SelectContainer />
        </Route>
        <Route exact path="/:id/places">
          <HotplacePage />
        </Route>
        <Route exact path="/:id/routes">
          <UsersRoutePage />
        </Route>
        <Route exact path="/myRoute">
          <MyRoutePage />
        </Route>
        <Route path="/:id/routes/:id">
          <RouteDetailPage />
        </Route>

        {/* 테스트 */}
        <Route exact path="/test">
          <PlaceCard />
        </Route>
      </Router>
    </RecoilRoot>
  );
}

export default App;
