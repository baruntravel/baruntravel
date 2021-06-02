import React from "react";
import Header from "../../components/common/header/header";
import Navbar from "../../components/common/navbar/navbar";
import WishList from "../../components/routeMakerPage/wishList/wishList";
import styles from "./routeMakerPage.module.css";

const RouteMakerPage = (props) => {
  return (
    <div className={styles.RouteMakerPage}>
      <Header />
      <WishList />
      <Navbar />
    </div>
  );
};

export default RouteMakerPage;
