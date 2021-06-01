import "./App.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import MainPage from "./pages/mainPage/mainPage";
import RoutePage from "./pages/routeMapPage/routeMapPage";
import HotplacePage from "./pages/kakaoMapPage/kakaoMapPage";
import { RecoilRoot } from "recoil";
import RouteDetailPage from "./pages/routeDetailPage/routeDetailPage";
import PlaceDetailPage from "./pages/placeDetailPage/placeDetailPage";
import AllRoute from "./components/mainPage/selectRoute/allRoute/allRoute";
import AllArea from "./components/mainPage/selectArea/allArea/allArea";
import ReviewDetailPage from "./pages/reviewDetailPage/reviewDetailPage";
import OurPlacePage from "./pages/ourPlacePage/ourPlacePage";
import ComunityPage from "./pages/comunityPage/comunityPage";
import DetailProfilePage from "./pages/detailProfilePage/detailProfilePage";
import "antd/dist/antd.css";
// import { lazy } from "react";
// const RouteDetailPage = lazy(() =>
//   import("./pages/routeDetailPage/routeDetailPage")
// );

function App() {
  return (
    <div>
      <RecoilRoot>
        <Router>
          <Route exact path="/">
            <MainPage />
          </Route>

          <Route exact path="/start">
            <MainPage />
          </Route>

          <Route exact path="/kakaoPlace">
            <HotplacePage />
          </Route>

          <Route exact path="/routePickPlace">
            <OurPlacePage />
          </Route>

          <Route exact path="/place-all">
            <AllArea />
          </Route>

          <Route exact path="/route">
            <RoutePage />
          </Route>
          <Route exact path="/route-all">
            <AllRoute />
          </Route>

          <Route exact path="/route/:id">
            <RouteDetailPage />
          </Route>

          <Route exact path="/place/:id">
            <PlaceDetailPage />
          </Route>

          <Route exact path="/detailProfile">
            <DetailProfilePage />
          </Route>

          <Route exact path="/review/detail/:id">
            <ReviewDetailPage />
          </Route>

          <Route exact path="/test2">
            <ComunityPage />
          </Route>

          {/* 테스트 */}
          <Route exact path="/test">
            <ReviewDetailPage />
          </Route>
        </Router>
      </RecoilRoot>
    </div>
  );
}

export default App;
