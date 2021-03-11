import { memo } from "react";
import ReactDOM from "react-dom";

const Portal = memo(({ children }) => {
  const el = document.getElementById("portal");
  return ReactDOM.createPortal(children, el);
});

export default Portal;
