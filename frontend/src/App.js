import "./App.css";
import MainPage from "./pages/mainPage/mainPage";
import SelectAreaPage from "./pages/selectAreaPage/selectAreaPage";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import UsersRoutePage from "./pages/usersRoutePage/usersRoutePage";
import HotplacePage from "./pages/hotplacePage/hotplacePage";
import { RecoilRoot } from "recoil";
import MyRoutePage from "./pages/myRoutePage/myRoutePage";
import ReviewCard from "./components/reviewComponents/reviewList/reviewCard/reviewCard";

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
        <Route exact path="/myRoute">
          <MyRoutePage />
        </Route>
        {/* 테스트 */}
        <Route exact path="/test">
          <ReviewCard />
        </Route>
      </Router>
    </RecoilRoot>
  );
}

export default App;
