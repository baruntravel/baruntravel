import axios from "axios";

export const makeRoute = async (name, placesData) => {
  const places = placesData.map((place, index) => ({
    id: place.id,
    order: index,
  }));
  const data = {
    name,
    places,
  };
  console.log(places);
  return await axios
    .post(`/route`, data)
    .then((res) => res.data.id || true)
    .catch((error) => {
      console.error(`${error}`);
      return false;
    });
};
export const getMyRoute = () => {
  return axios
    .get(`/routes/my`)
    .then((res) => res.data.routes)
    .catch((error) => {
      console.error(error);
      throw new Error(`${error}`);
    });
};

export const getRouteDetail = (routeId) => {
  return axios
    .get(`/route/${routeId}`)
    .then((res) => res.data)
    .catch((error) => {
      console.error(error);
      return false;
    });
};

// 상위 루트 10개씩 불러오는
export const getFeaturedRoutes = (id) => {
  const routes = axios
    .get(`/route/${id}`)
    .then((res) => res.data)
    .catch((error) => {
      console.error(error);
      throw new Error(`${error}`);
    });
  return routes;
};

// 왼밑, 오위 좌표로 루트들 불러옴
export const getRoutesByRange = (ne, sw) => {
  const routes = axios
    .get(`/routes/?page=0&size=3&maxX=${ne.La}&minX=${sw.La}&maxY=${ne.Ma}&minY=${sw.Ma}`)
    .then((res) => res.data)
    .catch((error) => {
      console.error(error);
      throw new Error(`${error}`);
    });
  return routes;
};
