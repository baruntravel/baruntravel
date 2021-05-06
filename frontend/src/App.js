import "./App.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import MainPage from "./pages/mainPage/mainPage";
import LoginPage from "./pages/loginPage/loginPage";
import UsersRoutePage from "./pages/usersRoutePage/usersRoutePage";
import HotplacePage from "./pages/hotplacePage/hotplacePage";
import { RecoilRoot } from "recoil";
import MyRoutePage from "./pages/myRoutePage/myRoutePage";
import RouteDetailPage from "./pages/routeDetailPage/routeDetailPage";
import PlaceDetailPage from "./pages/placeDetailPage/placeDetailPage";
import AllRoute from "./pages/mainPage/selectRoute/allRoute/allRoute";
import AllArea from "./pages/mainPage/selectArea/allArea/allArea";
import DetailProfile from "./components/detailProfile/detailProfile";

function App() {
  return (
    <div>
      <RecoilRoot>
        <Router>
          <Route exact path="/">
            <MainPage />
          </Route>

          <Route exact path="/login">
            <LoginPage />
          </Route>

          <Route exact path="/start">
            <MainPage />
          </Route>

          <Route exact path="/place">
            <HotplacePage />
          </Route>
          <Route exact path="/place-all">
            <AllArea />
          </Route>

          <Route exact path="/route">
            <UsersRoutePage />
          </Route>
          <Route exact path="/route-all">
            <AllRoute />
          </Route>
          <Route exact path="/myRoute">
            <MyRoutePage />
          </Route>
          <Route exact path="/route/:id">
            <RouteDetailPage />
          </Route>

          <Route exact path="/place/:id">
            <PlaceDetailPage />
          </Route>

          <Route exact path="/detailProfile">
            <DetailProfile />
          </Route>

          {/* 테스트 */}
          <Route exact path="/test">
            <RouteDetailPage />
          </Route>
        </Router>
      </RecoilRoot>
    </div>
  );
}

export default App;
