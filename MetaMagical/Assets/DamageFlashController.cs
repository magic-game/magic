using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class DamageFlashController : MonoBehaviour {

	Image image;
	private Color red = new Color(0.6f, 0, 0, 0);
	public float flashTimeout;
	private float flashTimer = 0;

	// Use this for initialization
	void Start () {
		image = GetComponent<Image>();
		image.color = red;
	}
	
	// Update is called once per frame
	void Update () {
		float alpha = 0;
		if (flashTimer > 0) {
			flashTimer = flashTimer - Time.deltaTime;
			alpha = (flashTimer / flashTimeout);
		}
		red.a = alpha;
		image.color = red;
	}

	public void takeDamage() {
		flashTimer = flashTimeout;
	}
}
