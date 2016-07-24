using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using UnityStandardAssets.Characters.FirstPerson;

public class HealthBar : MonoBehaviour {

	public GameObject player;
	public Slider slider;

	void Start () {
		slider = GetComponentInChildren<Slider> ();
		slider.maxValue = player.GetComponent<FirstPersonController> ().maxHealth;
	}
	
	// Update is called once per frame
	void Update () {
		slider.value = player.GetComponent<FirstPersonController> ().health;
	}
}
