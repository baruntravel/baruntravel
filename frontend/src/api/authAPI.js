import axios from "axios";

export const onLogin = (email, password) => {
  const data = {
    email,
    password,
  };
  return axios
    .post("/auth/login", data)
    .then((res) => {
      const {
        accessToken,
        refreshToken,
        accessTokenExpiredAt,
        refreshTokenExpiredAt,
      } = res.data;
      axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      localStorage.setItem("accessTokenExpiredAt", accessTokenExpiredAt);
      localStorage.setItem("refreshTokenExpiredAt", refreshTokenExpiredAt);
      return axios
        .get("/auth/me")
        .then((res) => [true, res.data.email, res.data.name]);
    })
    .catch((error) => {
      console.error(error);
      return [false, "", ""];
    });
};

export const onRegister = async (formData) => {
  // const data = {
  //   name,
  //   email,
  //   password,
  // };
  const result = await axios
    .post("/auth/register", formData, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then((res) => {
      console.log(res);
      return true;
    })
    .catch((error) => {
      console.error(error);
      return false;
    });
  return result;
};

export const onLogout = () => {
  axios.defaults.headers.common["Authorization"] = undefined;
  window.localStorage.clear();
};
