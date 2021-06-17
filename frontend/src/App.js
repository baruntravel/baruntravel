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
import WishListPage from "./pages/wishListPage/wishListPage";
import RouteMakerPage from "./pages/routeMakerPage/routeMakerPage";
import WishListPortal from "./components/portal/wishListPortal/wishListPortal";
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

          <Route exact path="/route-map">
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

          <Route exact path="/mypage">
            <DetailProfilePage />
          </Route>

          <Route exact path="/review/detail/:id">
            <ReviewDetailPage />
          </Route>

          <Route exact path="/wishlist">
            <WishListPage />
          </Route>

          <Route exact path="/routemaker">
            <RouteMakerPage />
          </Route>

          <Route exact path="/community/:id">
            <ComunityPage />
          </Route>

          {/* 테스트 */}
          <Route exact path="/test">
            <WishListPortal />
          </Route>
        </Router>
      </RecoilRoot>
    </div>
  );
}

export default App;
