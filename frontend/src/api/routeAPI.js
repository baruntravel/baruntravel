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

export const postRoute = (areaID) => {
  axios
    .post(`/route`, {
      name: "TEST1",
      places: [
        {
          id: 123,
          image:
            "https://api.apjung.xyz/tour/images/tour/main_new/mvisual_img07.jpg",
          name: "강릉바닷가",
          url: "https://api.apjung.xyz/tour/index.do",
          x: 37.748125,
          y: 128.878996,
          order: 1,
        },
        {
          id: 124,
          image:
            "https://api.apjung.xyz/xdata/images/hotel/270x200/129750773.jpg?k=d338049190ff48b19261ee5f516ee563aaeb8aeb97c4774c1e171e402cf25891&o=",
          name: "강릉 어린이집",
          url:
            "https://api.apjung.xyz/go/south-korea/kr-best-gangneung-things-to-do",
          x: 37.74813,
          y: 128.8789333,
          order: 2,
        },
      ],
    })
    .then((res) => {
      console.log(res);
    })
    .catch((error) => {
      console.error(error);
    });
};
