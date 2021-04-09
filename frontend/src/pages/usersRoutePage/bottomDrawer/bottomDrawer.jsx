import { ClickAwayListener, SwipeableDrawer } from "@material-ui/core";
import { useState } from "react";
import styles from "./bottomDrawer.module.css";

const BottomDrawer = ({ routeItems, index }) => {
  const [toggleDrawer, setToggleDrawer] = useState(false);
  const handleDrawer = () => {
    setToggleDrawer(!toggleDrawer);
  };

  const places = Object.values(routeItems)[index].places;

  return (
    <div className={styles.container}>
      <button
        className={styles.drawerButton}
        onClick={(e) => {
          handleDrawer(e);
        }}
      >
        ▲
      </button>
      <SwipeableDrawer
        anchor="bottom"
        open={toggleDrawer}
        onOpen={() => handleDrawer()}
        onClose={() => handleDrawer()}
      >
        {places.map(({ placeName, placeUrl, addressName }, index) => {
          return (
            <div className={styles.drawerCard} key={index}>
              <h1>{placeName}</h1>
              <h2>{placeUrl}</h2>
              <h2>{addressName}</h2>
            </div>
          );
        })}
      </SwipeableDrawer>
    </div>
  );
};

export default BottomDrawer;
