import axios from "axios";

// region 추가
export const makeRoute = async (name, placesData, region) => {
  const places = placesData.map((place, index) => ({
    id: place.id,
    order: index + 1,
  }));
  const data = {
    name,
    region,
    places,
  };
  console.log(places);
  console.log(data);
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

// TODO : 상위 루트 N개씩 불러오는
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
  return axios
    .get(`/routes/coordinate?page=0&size=5&sortType=best&maxX=${ne.La}&minX=${sw.La}&maxY=${ne.Ma}&minY=${sw.Ma}`)
    .then((res) => res.data)
    .catch((error) => {
      console.error(error);
      return false;
      // throw new Error(`${error}`);
    });
};

export const onHandleRouteLike = (routeId) => {
  axios
    .post(`route/${routeId}/like`)
    .then((res) => true)
    .catch((error) => {
      console.error(error);
      throw new Error(`unExpected Error ${error}`);
    });
};

export const getRoutesByRegion = (page, size, sortType, region) => {
  const routes = axios
    .get(`/routes?page=${page}&size=${size}&sortType=${sortType}&region=${region}`)
    .then((res) => res.data)
    .catch((error) => {
      console.error(error);
      throw new Error(`${error}`);
    });
  return routes;
};
