import axios from "axios";

export const getRoute = (areaID) => {
  axios
    .get(`/route/123`)
    .then((res) => {
      console.log(res);
    })
    .catch((error) => {
      console.error(error);
    });
};

export const postEmpty = () => {
  axios
    .post(`/route/empty`, {
      name: "Test1234",
    })
    .then((res) => {
      console.log(res);
    })
    .catch((error) => {
      console.error(error);
    });
};

export const postRoute = (data) => {
  axios
    .post(`/route`, {
      name: "TEST1",
      places: data,
    })
    .then((res) => {
      console.log(res);
    })
    .catch((error) => {
      console.error(error);
    });
};
