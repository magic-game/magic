using UnityEngine;
using System.Collections;
using MagicEngine;
using AssemblyCSharp;
using System.Collections.Generic;

public class BallFabScript : MonoBehaviour, MagicEntity
{

	Rigidbody rb;
	private List<SpellEventListener> listeners;
	int health;
	public GameObject explosionPrefab;

	// Use this for initialization
	void Start ()
	{
		health = 1;
		rb = GetComponent<Rigidbody> ();
		listeners = new List<SpellEventListener> ();
	}
	
	// Update is called once per frame
	void Update ()
	{
		transform.LookAt(Camera.main.transform.position, Vector3.up);
		if (health <= 0) {
			Instantiate (explosionPrefab, transform.position, Quaternion.identity);
			Destroy (gameObject);
		}
	}

	public Transform getTransform() {
		return this.transform;
	}

	public Rigidbody getRigidbody() {
		return rb;
	}

	void OnCollisionEnter (Collision col) {
		MagicEntity ent = col.gameObject.GetComponent<EnemyController> ();
		if (ent != null) {
			this.sendEvent (SpellEventType.Collision, ent);
		}
		takeDamage (1);
	}

	public void AddEventListener(SpellEventListener listener) {
		listeners.Add (listener);
	}

	private void sendEvent(SpellEventType type, MagicEntity entity) {
		foreach (SpellEventListener listener in listeners) {
			listener.HandleEvent (type, entity);
		}
	}

	public void takeDamage(int amount) {
		health = health - amount;
	}
}

