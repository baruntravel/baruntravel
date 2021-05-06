import React, { useEffect, useState } from "react";
import { getMyRoute } from "../../../../api/routeAPI";

const MyRouteList = (props) => {
  const [loading, setLoading] = useState(true);
  const [myRoute, setMyRoute] = useState([]);
  useEffect(() => {
    async function getRoute() {
      // const result = await getMyRoute();
      // console.log(result);
      // if (result) {
      //   setMyRoute(result);
      // }
    }
    getRoute();
    setLoading(false);
  }, []);
  return <div>hi</div>;
};

export default MyRouteList;
