using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using UnityStandardAssets.Characters.FirstPerson;

public class EnergyBar : MonoBehaviour {

	public GameObject player;
	public Slider slider;

	void Start () {
		slider = GetComponentInChildren<Slider> ();
	}

	// Update is called once per frame
	void Update () {
		slider.value = player.GetComponent<FirstPersonController> ().energy;
	}
}
