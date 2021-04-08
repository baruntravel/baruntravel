import styles from "./routeCarousel.module.css";
import { Carousel } from "react-responsive-carousel";

const routeCarousel = () => {
  return (
    <div className={styles.container}>
      <Carousel />
    </div>
  );
};

export default routeCarousel;
