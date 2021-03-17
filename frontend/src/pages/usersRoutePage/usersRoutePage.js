import React from "react";
import UsersRouteMap from "../../components/usersRouteMap/usersRouteMap";

const UsersRoutePage = () => {
  const area = window.location.href.split("/")[3];
  return (
    <div>
      <h2>{area} 추천 루트</h2>
      <UsersRouteMap />
    </div>
  );
};

export default UsersRoutePage;
