import styles from "./navbar.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronLeft, faShoppingCart } from "@fortawesome/free-solid-svg-icons";

const Navbar = () => {
  return (
    <div className={styles.container}>
      <div className={styles.backButton}>
        <FontAwesomeIcon icon={faChevronLeft} color="black" size="lg" />
      </div>
      <div className={styles.cartButton}>
        <FontAwesomeIcon icon={faShoppingCart} color="black" size="lg" />
      </div>
    </div>
  );
};

export default Navbar;
