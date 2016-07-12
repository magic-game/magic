using UnityEngine;
using System.Collections;

public class ExplosionController : MonoBehaviour {

	void Start() {
		var exp = GetComponent<ParticleSystem>();
		exp.Play();
		Destroy(gameObject, exp.duration);
	}
	
	// Update is called once per frame
	void Update () {
	}
}
