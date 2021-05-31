import React from "react";
import MyRouteList from "../myRouteList/myRouteList";

const DetailProfileCondition = ({ selected }) => {
  if (selected === "내경로") {
    return <MyRouteList />;
  }
  if (selected === "공유경로") {
    return <div>공유 경로</div>;
  }
  if (selected === "좋아요") {
    return <div>좋아요 경로</div>;
  }
  if (selected === "리뷰") {
    return <div>리뷰</div>;
  }
};
export default DetailProfileCondition;
