import { SwipeableDrawer } from "@material-ui/core";
import { useState } from "react";
import styles from "./bottomDrawer.module.css";

const BottomDrawer = ({ routes, index }) => {
  const [toggleDrawer, setToggleDrawer] = useState(false);
  const handleDrawer = () => {
    setToggleDrawer(!toggleDrawer);
  };

  const places = Object.values(routes)[index].places;

  return (
    <div className={styles.container}>
      <button
        className={[styles.drawerButton, styles.up].join(" ")}
        onClick={(e) => {
          handleDrawer(e);
        }}
      >
        ▲
      </button>
      <SwipeableDrawer anchor="bottom" open={toggleDrawer} onOpen={() => handleDrawer()} onClose={() => handleDrawer()}>
        <button
          className={[styles.drawerButton, styles.down].join(" ")}
          onClick={(e) => {
            handleDrawer(e);
          }}
        >
          ▼
        </button>
        {places.map(({ placeName, placeUrl, addressName }, index) => {
          return (
            <div className={styles.drawerCard} key={index}>
              <h2>{placeName}</h2>
              <h3>{placeUrl}</h3>
              <h3>{addressName}</h3>
            </div>
          );
        })}
      </SwipeableDrawer>
    </div>
  );
};

export default BottomDrawer;
