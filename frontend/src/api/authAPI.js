import axios from "axios";

export const onLogin = async (email, password) => {
  const data = {
    email,
    password,
  };
  await axios
    .post("/auth/login", data)
    .then((res) => {
      const { accessToken, refreshToken, expiredTime } = res.data;
      axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
      localStorage.setItem("refreshToken", refreshToken);
      localStorage.setItem("expiredTime", expiredTime);
      return axios
        .get("/auth/me")
        .then((res) => [true, res.data.email, res.data.name]);
    })
    .catch((error) => {
      console.error(error);
      return [false, "", ""];
    });
};

export const onRegister = async (name, email, password) => {
  const data = {
    name,
    email,
    password,
  };
  await axios
    .post("/auth/register", data)
    .then((res) => {
      console.log(res);
      return true;
    })
    .catch((error) => {
      console.error(error);
      return false;
    });
};
