import "./App.css";
import MainPage from "./pages/mainPage/mainPage";
import SelectContainer from "./pages/selectContainer/selectContainer";
import { BrowserRouter as Router, Route } from "react-router-dom";
import UsersRoutePage from "./pages/usersRoutePage/usersRoutePage";
import HotplacePage from "./pages/hotplacePage/hotplacePage";
import { RecoilRoot } from "recoil";
import MyRoutePage from "./pages/myRoutePage/myRoutePage";
import RouteDetailPage from "./pages/routeDetailPage/routeDetailPage";
import PlaceDetailPage from "./pages/placeDetailPage/placeDetailPage";
import AllRoute from "./pages/selectContainer/selectRoute/allRoute/allRoute";
import AllArea from "./pages/selectContainer/selectArea/allArea/allArea";
import SideMyProfile from "./components/sideMyProfile/sideMyProfile";
import { Drawer } from "antd";

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
        <Route exact path="/all-place">
          <AllArea />
        </Route>

        <Route exact path="/routes">
          <UsersRoutePage />
        </Route>
        <Route exact path="/all-route">
          <AllRoute />
        </Route>
        <Route exact path="/myRoute">
          <MyRoutePage />
        </Route>
        <Route path="/routes/:id">
          <RouteDetailPage />
        </Route>

        <Route exact path="/:id/place/detail">
          <PlaceDetailPage />
        </Route>

        {/* 테스트 */}
        <Route exact path="/test">
          <Drawer
            placement="right"
            closable={true}
            visible={true}
            width={window.innerWidth > 768 ? "36vw" : "80vw"}
            bodyStyle={{ padding: 0 }}
          >
            <SideMyProfile />
          </Drawer>
        </Route>
      </Router>
    </RecoilRoot>
  );
}

export default App;
